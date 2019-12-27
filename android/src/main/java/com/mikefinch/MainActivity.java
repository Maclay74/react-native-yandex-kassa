package com.mikefinch.yandexkassa;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.CallSuper;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableNativeArray;

import com.mikefinch.yandexkassa.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
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

/**
 * Activity to start from React Native JavaScript, triggered via
 * {@link ActivityStarterModule#navigateToExample()}.
 */
public final class MainActivity extends ReactActivity {

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("99999.99");
    private static final Currency RUB = Currency.getInstance("RUB");
    private static final String KEY_AMOUNT = "amount";
    private static final int REQUEST_CODE_TOKENIZE = 33;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Receive token from mSDK

        if (requestCode == REQUEST_CODE_TOKENIZE) {
            switch (resultCode) {
                case RESULT_OK:
                    // successful tokenization
                    //final TokenizationResult result = Checkout.createTokenizationResult(data);
                    //startActivity(SuccessTokenizeActivity.createIntent(
                    //        this, result.getPaymentToken(), result.getPaymentMethodType().name()));
                    break;
                case RESULT_CANCELED:
                    // user canceled tokenization
                    //Toast.makeText(this, R.string.tokenization_canceled, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}