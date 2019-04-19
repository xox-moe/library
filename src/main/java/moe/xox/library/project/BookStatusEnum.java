package moe.xox.library.project;

public enum BookStatusEnum {

    OUT(1,"借出"),
    NORMAL(0,"正常"),
    LOSE(2,"丢失"),
    DESTROY(3,"损坏");
//    OUT(1,"借出");

    public long id;
    public String name;

    BookStatusEnum(int id,String name){
        this.id = id;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
