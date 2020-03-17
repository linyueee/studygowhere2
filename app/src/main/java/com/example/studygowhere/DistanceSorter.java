package com.example.studygowhere;

import java.util.Comparator;

public class DistanceSorter implements Comparator<StudyArea> {


    @Override
    public int compare(StudyArea o1, StudyArea o2) {
        return o1.getDistance().compareTo(o2.getDistance());
    }
}
