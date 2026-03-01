package org.project.lab.alternative;

import org.project.lab.service.UniversityService;
import org.project.lab.entity.University;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@Alternative
@RequestScoped
public class AdvancedUniversityService extends UniversityService {

  private static final Logger LOGGER = Logger.getLogger(AdvancedUniversityService.class.getName());

  @Override
  @Transactional
  public void addUniversity(University university) {
    LOGGER.info("Advanced validation for: " + university.getName());
    if (university.getName() == null || university.getName().isEmpty()) {
      throw new IllegalArgumentException("University name cannot be empty");
    }
    super.addUniversity(university);
  }
}