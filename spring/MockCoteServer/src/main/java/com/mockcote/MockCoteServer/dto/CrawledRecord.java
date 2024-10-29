package com.mockcote.MockCoteServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawledRecord {
	private int submission_id;
	String handle;
	String result;
	private int performance;
	private String language;
}
