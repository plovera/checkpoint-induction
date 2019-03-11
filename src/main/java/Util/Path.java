package Util;

public class Path {

    public static class Web {
        public static final String HOME = "/home";
        public static final String CREATE_PREFERENCE = "/createPreference";
        public static final String PREFERENCE = "/preference";
        public static final String TOKENIZE_V1 = "/tokenizev1";
        public static final String PAYMENT = "/payment";
        public static final String TOKENIZE_V2 = "/tokenizev2";
        public static final String PROCESS_PAY = "/process_pay";
        public static final String PREFERENCE_V2 = "/preferencev2";
        public static final String PROCESS_PREFERENCE = "/process_preference";

    }

    public static class Template {
        public static final String HOME = "/velocity/home.vm";
        public static final String PREFERENCE = "/velocity/preference.vm";
        public static final String TOKENIZE = "/velocity/tokenize.vm";
        public static final String TOKENIZE_V2 = "/velocity/tokenizev2.vm";
        public static final String PREFERENCE_V2 = "/velocity/preferencev2.vm";
    }
}
