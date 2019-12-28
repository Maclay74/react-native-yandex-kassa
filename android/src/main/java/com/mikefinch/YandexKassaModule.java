package com.mikefinch.yandexkassa;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableNativeArray;

import com.mikefinch.yandexkassa.settings.Settings;

import ru.yandex.money.android.sdk.Amount;
import ru.yandex.money.android.sdk.Checkout;
import ru.yandex.money.android.sdk.ColorScheme;
import ru.yandex.money.android.sdk.PaymentMethodType;
import ru.yandex.money.android.sdk.PaymentParameters;
import ru.yandex.money.android.sdk.MockConfiguration;
import ru.yandex.money.android.sdk.SavePaymentMethod;
import ru.yandex.money.android.sdk.TestParameters;
import ru.yandex.money.android.sdk.TokenizationResult;
import ru.yandex.money.android.sdk.UiParameters;


public class YandexKassaModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private boolean testMode = false;

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("99999.99");
    private static final Currency RUB = Currency.getInstance("RUB");
    private static final String KEY_AMOUNT = "amount";
    private static final int REQUEST_CODE_TOKENIZE = 33;
    private static final int REQUEST_CODE_3DSECURE = 35;
    private Promise paymentPromise;


    public YandexKassaModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "YandexKassa";
    }

    @ReactMethod
    public void finish() {
        // Need for iOS
    }

    @ReactMethod
    public void show3Dsecure(String url, Promise promise) {
        this.paymentPromise = promise;
        Intent intent = Checkout.create3dsIntent(this.reactContext,url);
        Activity activity = getCurrentActivity();

        if (activity == null) {
            paymentPromise.reject("Activity doesn't exist");
            return;
        }

        activity.startActivityForResult(intent, REQUEST_CODE_3DSECURE);
    }

    @ReactMethod
    public void payment(String productName, String productDescription, String amount, Promise promise) {
        
        final Settings settings = new Settings(this.reactContext);
        this.paymentPromise = promise;

        final Set<PaymentMethodType> paymentMethodTypes = getPaymentMethodTypes(settings);

        final PaymentParameters paymentParameters = new PaymentParameters(
            new Amount(new BigDecimal(amount), RUB),
            productName,
            productDescription,
            this.reactContext.getResources().getString(R.string.yandex_kassa_token),
            this.reactContext.getResources().getString(R.string.yandex_kassa_shop_id),
            settings.getSavePaymentMethod(),
            paymentMethodTypes
        );

        final UiParameters uiParameters = new UiParameters(
                    settings.showYandexCheckoutLogo(), new ColorScheme(settings.getPrimaryColor()));

        final MockConfiguration mockConfiguration;

        if (settings.isTestModeEnabled()) {
            mockConfiguration = new MockConfiguration(settings.shouldCompletePaymentWithError(),
            settings.isPaymentAuthPassed(),
            settings.getLinkedCardsCount(),
            new Amount(new BigDecimal(settings.getServiceFee()), RUB));
        } else {
            mockConfiguration = null;
        }

        final TestParameters testParameters = new TestParameters(true, false, mockConfiguration);


        Activity activity = getCurrentActivity();

        if (activity == null) {
          paymentPromise.reject("Activity doesn't exist");
          return;
        }

        final Intent intent = Checkout.createTokenizeIntent(this.reactContext,
            paymentParameters,
            testParameters,
            uiParameters
        );
        activity.startActivityForResult(intent, REQUEST_CODE_TOKENIZE);
    }

    @NonNull
    private static Set<PaymentMethodType> getPaymentMethodTypes(Settings settings) {
        final Set<PaymentMethodType> paymentMethodTypes = new HashSet<>();

        if (settings.isYandexMoneyAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.YANDEX_MONEY);
        }

        if (settings.isNewCardAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.BANK_CARD);
        }

        if (settings.isSberbankOnlineAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.SBERBANK);
        }

        if (settings.isGooglePayAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.GOOGLE_PAY);
        }

        return paymentMethodTypes;
    }

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
          if (requestCode == REQUEST_CODE_TOKENIZE) {

            switch(resultCode) {
                case Activity.RESULT_OK:
                    final TokenizationResult result = Checkout.createTokenizationResult(data);
                    String token = result.getPaymentToken();
                    paymentPromise.resolve(token);
                    //Toast.makeText(activity, token, 200000).show();
                break;

                case Activity.RESULT_CANCELED:
                    paymentPromise.reject("Payment cancelled");
                break;
            }
          }

          if (requestCode == REQUEST_CODE_3DSECURE) {
           switch(resultCode) {
                case Activity.RESULT_OK:
                     paymentPromise.resolve(true);
                 break;

                 case Activity.RESULT_CANCELED:
                 case Checkout.RESULT_ERROR:
                     paymentPromise.reject("Payment cancelled");
                 break;
                 }
           }

        }
      };

}
