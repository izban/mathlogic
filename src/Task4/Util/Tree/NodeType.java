package Task4.Util.Tree;

/**
 * Created by izban on 21.05.2016.
 */
public enum NodeType {
    IMPL {
        @Override
        public String toString() {
            return "->";
        }
    },
    OR {
        @Override
        public String toString() {
            return "|";
        }
    },
    AND {
        @Override
        public String toString() {
            return "&";
        }
    },
    NOT {
        @Override
        public String toString() {
            return "!";
        }
    },
    VARIABLE {
        @Override
        public String toString() {
            throw new AssertionError();
            //return "";
        }
    },
    FORALL {
        @Override
        public String toString() {
            return "@";
        }
    },
    EXIST {
        @Override
        public String toString() {
            return "?";
        }
    },
    PREDICAT_EQUAL {
        @Override
        public String toString() {
            return "=";
        }
    },
    PREDICAT_NAMED {
        @Override
        public String toString() {
            throw new AssertionError();
            //return "";
        }
    },
    ADD {
        @Override
        public String toString() {
            return "+";
        }
    },
    MULTIPLY {
        @Override
        public String toString() {
            return "*";
        }
    },
    ZERO {
        @Override
        public String toString() {
            return "0";
        }
    },
    INC {
        @Override
        public String toString() {
            return "'";
        }
    },
    FUNCTION {
        @Override
        public String toString() {
            throw new AssertionError();
            //return "";
        }
    }
}
