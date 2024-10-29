package com.mockcote.MockCoteServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawledRecord {
	private int submission_id;
	private String handle;
	private String result;
	private int performance;
	private String language;
}
