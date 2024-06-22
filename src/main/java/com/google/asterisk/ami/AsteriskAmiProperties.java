package com.aws.asterisk.ami;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsteriskAmiProperties
{
	private String host;
	private String username;
	private String password;
}
