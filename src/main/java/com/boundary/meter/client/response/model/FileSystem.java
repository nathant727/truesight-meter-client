package com.boundary.meter.client.response.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableFileSystem.class)
@Value.Immutable
public abstract class FileSystem {

    public abstract String dirName();
    public abstract String devName();
    public abstract String typeName();
    public abstract String sysTypeName();

}
