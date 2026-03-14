package org.project.lab.decorator;

import org.project.lab.entity.University;
import org.project.lab.service.UniversityServiceLocal;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Decorator
public abstract class UniversityServiceDecorator implements UniversityServiceLocal {

  private static final Logger LOGGER = Logger.getLogger(UniversityServiceDecorator.class.getName());

  @Inject
  @Delegate
  @Any
  private UniversityServiceLocal delegate;

  @Override
  public List<University> getAllUniversities() {
    LOGGER.info("[Decorator] Before getting all universities");
    List<University> result = delegate.getAllUniversities();
    LOGGER.info("[Decorator] Found " + result.size() + " universities");
    return result;
  }
}
