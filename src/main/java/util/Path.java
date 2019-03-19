package util;

public class Path {

    public static class Web {
        public static final String HOME = "/home";

        public static final String API_PREFERENCE = "/preference";
        public static final String CREATE_PREFERENCE = API_PREFERENCE + "/create";
        public static final String REDIRECT_PREFERENCE = API_PREFERENCE + "/redirect";
        public static final String PREFERENCE_V1 = API_PREFERENCE + "/v1";
        public static final String PREFERENCE_V2 = API_PREFERENCE + "/v2";
        public static final String PREFERENCE_PROCESS = API_PREFERENCE + "/process";

        public static final String API_TOKENIZE = "/tokenize";
        public static final String TOKENIZE_V1 = API_TOKENIZE + "/v1";
        public static final String TOKENIZE_V2 = API_TOKENIZE + "/v2";
        public static final String TOKENIZE_PAYMENT = API_TOKENIZE + "/payment";


    }

    public static class Template {
        public static final String HOME = "/velocity/home.vm";
        public static final String PREFERENCE = "/velocity/preference.vm";
        public static final String TOKENIZE = "/velocity/tokenize.vm";
        public static final String TOKENIZE_V2 = "/velocity/tokenizev2.vm";
        public static final String PREFERENCE_V2 = "/velocity/preferencev2.vm";
    }
}
