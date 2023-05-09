package ru.stqa.pft.sandbox;
import org.testng.Assert;
import org.testng.annotations.Test;
public class PointTests {


    //= 5
    @Test
    public void testDistance_successReturn() {
        Point p1 = new Point(6, 6);
        Point p2 = new Point(2, 3);
        Assert.assertEquals(p1.distance(p2), 5.0);
        }
    //= 5
    @Test
    public void testDistance_notsuccessReturn() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(4, -2);
        Assert.assertNotEquals(p1.distance(p2), 7.0);
        }

    }

