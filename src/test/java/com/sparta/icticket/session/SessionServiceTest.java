package com.sparta.icticket.session;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void getSessions() {
       //안해도됨
    }
}