package converter;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String>{

    @Override
    public String convertToDatabaseColumn(Boolean b) {
        if (b.equals(Boolean.TRUE))
            return "Y";
        else
            return "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String s) {
        if (s == null)
            return true;
        if(s.equalsIgnoreCase("Y")||s.equalsIgnoreCase("true")||s.equalsIgnoreCase("1"))
            return true;

        return false;
    }
}
