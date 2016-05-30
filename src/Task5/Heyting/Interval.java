package Task5.Heyting;

/**
 * Created by izban on 29.05.2016.
 */
class Interval implements Comparable<Interval> {
    final int left;
    final int right;
    final boolean incL;
    final boolean incR;

    Interval(int left, boolean incL, int right, boolean incR) {
        this.left = left;
        this.incL = incL;
        this.right = right;
        this.incR = incR;
    }

    @Override
    public int compareTo(Interval o) {
        if (left != o.left) return Integer.compare(left, o.left);
        if (incL != o.incL) return -Boolean.compare(incL, o.incL);
        if (right != o.right) return Integer.compare(right, o.right);
        return -Boolean.compare(incR, o.incR);
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append(incL ? "[" : "(");
        ans.append(left);
        ans.append(",");
        ans.append(right);
        ans.append(incR ? "]" : ")");
        return ans.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Interval)) return false;
        Interval o = (Interval)obj;
        return left == o.left && incL == o.incL && right == o.right && incR == o.incR;
    }
}
