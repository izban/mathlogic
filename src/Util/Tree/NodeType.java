package Util.Tree;

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
            return "";
        }
    }
}
