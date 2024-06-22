package com.aws.asterisk.agi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsteriskAgiProperties
{
    private int port;
    private String host;
    private int poolSize;
    private int maximumPoolSize;
}
