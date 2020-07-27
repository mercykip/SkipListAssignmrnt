package java;

public class Main {
    public static void main(String[] args) {
        SkipList<Integer, String> skipList = new SkipList<Integer, String>(1);
        for (int i = 0; i < 10; i++) {
            skipList.add(i, String.valueOf(i));
        }
        assert skipList.size() == 10;
        assert !skipList.empty();
        assert !skipList.contains(11);
        assert skipList.get(5).equals("5");
        int count = 0;
        for (Integer i : skipList)
            assert i.equals(count++);
        skipList.remove(9);
        assert skipList.size() == 9;
        assert skipList.get(9) == null;
        skipList.remove(8);
        skipList.remove(7);
        skipList.remove(6);
        skipList.remove(5);
        skipList.remove(4);
        skipList.remove(3);
        skipList.remove(2);
        skipList.remove(1);
        skipList.remove(0);
        assert skipList.size() == 0;
        assert skipList.empty();
    }
}
