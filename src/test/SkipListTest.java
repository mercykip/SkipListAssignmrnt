package test;

import org.junit.Test;

import java.SkipList;
import java.util.List;

import static org.junit.Assert.*;

public class SkipListTest {
    @Test
    public void size(){
        SkipList<Integer,String> skipList = new SkipList<Integer,String>(1);
        int s=10;
        assert skipList.size() == 10;

    }
    @Test
    public void add() {

    System.out.println("skiptlist");
    }

    @Test
    public void remove() {

    }

    @Test
    public void empty() {
    }


}