package com.hdc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class Movie {
    private int id;
    private String mvName;
    private String mvImg;
    private String mvDirector;
    private String mvPerformer;
    private String mvType;
    private String mvStart;
    private String mvIntroduce;
    private String mvTime;
    private int mvStatus;
    private int mvLike;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", mvName='" + mvName + '\'' +
                ", mvImg='" + mvImg + '\'' +
                ", mvDirector='" + mvDirector + '\'' +
                ", mvPerformer='" + mvPerformer + '\'' +
                ", mvType='" + mvType + '\'' +
                ", mvStart='" + mvStart + '\'' +
                ", mvIntroduce='" + mvIntroduce + '\'' +
                ", mvTime='" + mvTime + '\'' +
                ", mvStatus=" + mvStatus +
                ", mvLike=" + mvLike +
                '}';
    }
}
