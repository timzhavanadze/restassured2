package Utils;

public class CustomInclude {
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof String){
            return false;
        }
        return true;
    }
}
