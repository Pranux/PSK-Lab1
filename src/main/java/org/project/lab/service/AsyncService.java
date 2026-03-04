package org.project.lab.service;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.concurrent.Future;

@Stateless
public class AsyncService {

  @Asynchronous
  public Future<String> longRunningOperation() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return new AsyncResult<>("Operation completed!");
  }
}