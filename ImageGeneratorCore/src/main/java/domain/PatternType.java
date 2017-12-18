package domain;

public enum PatternType {

    COMMONS {
        @Override
        public String location() {
            return "images/colors";
        }
    },

    FLAGS {
        @Override
        public String location() {
            return "images/flags";
        }
    },

    PLAINS {
        @Override
        public String location() {
            return "images/plains";
        }
    };

    public abstract String location();
}
