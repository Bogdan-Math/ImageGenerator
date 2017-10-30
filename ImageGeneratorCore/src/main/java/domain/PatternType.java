package domain;

public enum PatternType {

    COMMONS {
        @Override
        public String getLocation() {
            return "images/colors";
        }
    },

    FLAGS {
        @Override
        public String getLocation() {
            return "images/flags";
        }
    },

    PLAINS {
        @Override
        public String getLocation() {
            return "images/plains";
        }
    };

    public abstract String getLocation();
}
