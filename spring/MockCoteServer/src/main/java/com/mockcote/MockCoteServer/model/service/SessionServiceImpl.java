package com.mockcote.MockCoteServer.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.crawler.Crawler;
import com.mockcote.MockCoteServer.dto.Problem;
import com.mockcote.MockCoteServer.dto.Session;
import com.mockcote.MockCoteServer.dto.User;
import com.mockcote.MockCoteServer.model.mapper.QueryMapper;
import com.mockcote.MockCoteServer.model.mapper.SessionMapper;
import com.mockcote.MockCoteServer.model.mapper.SessionTrackerMapper;
import com.mockcote.MockCoteServer.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
	

	

}
