package tech.weizhang.draft;

import lombok.Data;

import java.io.Serializable;

public class TestDto implements Serializable {
    private String name;
    private Long id;

    public TestDto(String name,Long id){
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{name:"+this.name+",id:"+this.id+"}";
    }
}
