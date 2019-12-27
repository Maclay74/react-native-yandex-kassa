package com.mikefinch.yandexkassa.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import ru.yandex.money.android.sdk.SavePaymentMethod;

public final class Settings {

    static final String KEY_LINKED_CARDS_COUNT = "linked_cards_count";
    static final String KEY_PRIMARY_COLOR_RED_VALUE = "primary_color_red_value";
    static final String KEY_PRIMARY_COLOR_GREEN_VALUE = "primary_color_green_value";
    static final String KEY_PRIMARY_COLOR_BLUE_VALUE = "primary_color_blue_value";
    static final String KEY_YANDEX_MONEY_ALLOWED = "yandex_money_allowed";
    static final String KEY_SBERBANK_ONLINE_ALLOWED = "sberbank_online_allowed";
    static final String KEY_GOOGLE_PAY_ALLOWED = "google_pay_allowed";
    static final String KEY_NEW_CARD_ALLOWED = "new_card_allowed";
    static final String KEY_SHOW_YANDEX_CHECKOUT_LOGO = "show_yandex_checkout_logo";
    static final String KEY_AUTOFILL_USER_PHONE_NUMBER = "autofill_user_phone_number";
    static final String KEY_TEST_MODE_ENABLED = "test_mode_enabled";
    static final String KEY_PAYMENT_AUTH_PASSED = "payment_auth_passed";
    static final String KEY_SERVICE_FEE = "fee";
    static final String KEY_SHOULD_COMPLETE_PAYMENT_WITH_ERROR = "should_complete_with_error";
    static final String KEY_SAVE_PAYMENT_METHOD = "save_payment_method";

    private SharedPreferences sp;

    public Settings(@NonNull Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isYandexMoneyAllowed() {
        return sp.getBoolean(KEY_YANDEX_MONEY_ALLOWED, true);
    }

    public boolean isSberbankOnlineAllowed() {
        return sp.getBoolean(KEY_SBERBANK_ONLINE_ALLOWED, true);
    }

    public boolean isGooglePayAllowed() {
        return sp.getBoolean(KEY_GOOGLE_PAY_ALLOWED, true);
    }

    public boolean isNewCardAllowed() {
        return sp.getBoolean(KEY_NEW_CARD_ALLOWED, true);
    }

    public boolean showYandexCheckoutLogo() {
        return sp.getBoolean(KEY_SHOW_YANDEX_CHECKOUT_LOGO, true);
    }

    public boolean autofillUserPhoneNumber() {
        return sp.getBoolean(KEY_AUTOFILL_USER_PHONE_NUMBER, false);
    }

    public boolean isTestModeEnabled() {
        return sp.getBoolean(KEY_TEST_MODE_ENABLED, false);
    }

    public boolean isPaymentAuthPassed() {
        return sp.getBoolean(KEY_PAYMENT_AUTH_PASSED, false);
    }

    public float getServiceFee() {
        return sp.getFloat(KEY_SERVICE_FEE, 0f);
    }

    public int getLinkedCardsCount() {
        return sp.getInt(KEY_LINKED_CARDS_COUNT, 1);
    }

    public SavePaymentMethod getSavePaymentMethod() {
        return getSavePaymentMethod(getSavePaymentMethodId());
    }

    int getSavePaymentMethodId() {
        return sp.getInt(KEY_SAVE_PAYMENT_METHOD, 0);
    }

    @ColorInt
    public int getPrimaryColor() {
        return Color.rgb(
                sp.getInt(KEY_PRIMARY_COLOR_RED_VALUE, 0),
                sp.getInt(KEY_PRIMARY_COLOR_GREEN_VALUE, 114),
                sp.getInt(KEY_PRIMARY_COLOR_BLUE_VALUE, 245)
        );
    }

    public boolean shouldCompletePaymentWithError() {
        return sp.getBoolean(KEY_SHOULD_COMPLETE_PAYMENT_WITH_ERROR, false);
    }

    private static SavePaymentMethod getSavePaymentMethod(int value) {
        SavePaymentMethod savePaymentMethod;
        switch (value) {
            case 0:
                savePaymentMethod = SavePaymentMethod.USER_SELECTS;
                break;
            case 1:
                savePaymentMethod = SavePaymentMethod.ON;
                break;
            case 2:
                savePaymentMethod = SavePaymentMethod.OFF;
                break;
            default:
                savePaymentMethod = SavePaymentMethod.USER_SELECTS;
        }
        return savePaymentMethod;
    }
}
