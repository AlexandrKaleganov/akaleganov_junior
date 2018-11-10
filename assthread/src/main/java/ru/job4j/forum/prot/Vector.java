package ru.job4j.forum.prot;

public class Vector {
    private int x;
    private int y;
    Vector3d vector3d;

    Vector(int x, int y) {
        this.vector3d = new Vector3d() {

            @Override
            public int sum() {
                return x + y;
            }

            @Override
            public int mul() {
                return x * y;
            }
        };
        this.x = x;
        this.y = y;
    }

    protected void sum() {
        this.vector3d.sum();
    }


    protected void mult() {
        this.vector3d.mul();
    }
}
