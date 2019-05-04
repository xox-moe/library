package moe.xox.library.project;


public enum BookStatusEnum {

    OUT(1,"借出"),
    NORMAL(4,"正常"),
    LOSE(2,"丢失"),
    DESTROY(3,"损坏");

    public static String getNameById(int id){
        String[] strings = {"","借出","丢失","损坏","正常"};
        try {
            return strings[id];
        }catch (NullPointerException npe){
            System.out.print("出现了空指针异常 参数:"+id);
            return null;
        }
    }

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
