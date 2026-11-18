package infrastructure;

public class BytesConverter{
    public static String SmartConvert(long bytes){
        if(bytes/Math.pow(1024, 3) >= 1)
            return Math.round(bytes/Math.pow(1024, 3)*100)/100.0 + "Gb";
        else
            return Math.round(bytes/Math.pow(1024, 2)*100)/100.0 + "Mb";
    }
}