package services;


import java.util.List;

public interface ClassConfigurationService {
    boolean isClassLimitReached(List<?> students);
    int getPassingMarks();
}
