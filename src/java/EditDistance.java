public class EditDistance {
    public static void main(String[] args) throws Exception {
        System.out.println(distance("11115354/2015-5", "014.252/2015-5"));
    }

    public static int distance(String s1, String s2) {
        int edits[][] = new int[s1.length() + 1][s2.length() + 1];

        int lenghtStringOne = s1.length();
        int lenghtStringTwo = s2.length();

        for (int i = 0; i <= lenghtStringOne; i++) {
            edits[i][0] = i;
        }

        for (int j = 1; j <= lenghtStringTwo; j++) {
            edits[0][j] = j;
        }

        for (int i = 1; i <= lenghtStringOne; i++) {
            for (int j = 1; j <= lenghtStringTwo; j++) {
                int insertion = edits[i][j - 1] + 1;
                int deletion = edits[i - 1][j] + 1;
                int u = (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1);
                int change = edits[i - 1][j - 1] + u;

                edits[i][j] = Math.min(
                        insertion,
                        Math.min(
                                deletion,
                                change
                                )
                        );
            }
        }
        return edits[lenghtStringOne][lenghtStringTwo];
    }
}

