package com.example.mockPortoData;

record ProjectInterfaceItem(
    long id, 
    String title, 
    String description
){

}

public record ProjectInterface(
    ProjectInterfaceItem[] data
    ) { }
