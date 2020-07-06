package tech.weizhang.learn.repo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestDto implements Serializable {
    private String name;
    private Long id;

    public TestDto(String name,Long id){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{name:"+this.name+",id:"+this.id+"}";
    }
}
