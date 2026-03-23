package org.project.lab.controller;

import org.project.lab.entity.Course;
import org.project.lab.entity.Student;
import org.project.lab.entity.University;
import org.project.lab.service.AsyncService;
import org.project.lab.service.UniversityServiceLocal;
//import org.project.lab.dao.StudentDAOJPA;
//import org.project.lab.dao.CourseDAOJPA;
import org.project.lab.dao.StudentDAO;
import org.project.lab.dao.CourseDAO;

import java.util.concurrent.Future;

import javax.faces.view.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.persistence.OptimisticLockException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class UniversityController implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private UniversityServiceLocal universityService;

  @Inject
//  private StudentDAOJPA studentDAO;
  private StudentDAO studentDAO;

  @Inject
//  private CourseDAOJPA courseDAO;
  private CourseDAO courseDAO;

  @Inject
  private AsyncService asyncService;

  // transient: Future is not serializable, but @ViewScoped keeps it in memory
  private transient Future<String> asyncFuture;
  private String asyncStatus = "";

  private Long formStudentId;
  private Student formStudent = new Student();
  private Long selectedUniversityId;
  private List<Long> selectedCourseIds = new ArrayList<>();
  private String errorMessage;
  private boolean hasConflict;

  // --- Async demo ---

  public String getAsyncStatus() { return asyncStatus; }

  public void startAsyncOperation() {
    asyncFuture = asyncService.longRunningOperation();
    asyncStatus = "Operation started — page is still responsive! Click 'Check result' after ~5 seconds.";
  }

  public void checkAsyncResult() {
    if (asyncFuture == null) {
      asyncStatus = "No operation started yet.";
    } else if (asyncFuture.isDone()) {
      try {
        asyncStatus = "Completed! Result: " + asyncFuture.get();
      } catch (Exception e) {
        asyncStatus = "Error: " + e.getMessage();
      }
    } else {
      asyncStatus = "Still running...";
    }
  }

  // --- List page ---

  public List<University> getUniversities() {
    return universityService.getAllUniversities();
  }

  public List<Student> getStudents() {
    return studentDAO.findAll();
  }

  // --- Shared form state ---

  public Long getFormStudentId() { return formStudentId; }
  public void setFormStudentId(Long formStudentId) { this.formStudentId = formStudentId; }

  public Student getFormStudent() { return formStudent; }

  public Long getSelectedUniversityId() { return selectedUniversityId; }
  public void setSelectedUniversityId(Long selectedUniversityId) { this.selectedUniversityId = selectedUniversityId; }

  public List<Long> getSelectedCourseIds() { return selectedCourseIds; }
  public void setSelectedCourseIds(List<Long> selectedCourseIds) { this.selectedCourseIds = selectedCourseIds; }

  public String getErrorMessage() { return errorMessage; }
  public boolean isHasConflict() { return hasConflict; }

  public List<Course> getFilteredCourses() {
    if (selectedUniversityId == null) return Collections.emptyList();
    return courseDAO.findByUniversity(selectedUniversityId);
  }

  // --- Edit page ---

  /** Loads the student and pre-selects university + courses already enrolled in. */
  public void initForm(ComponentSystemEvent event) {
    if (formStudentId != null) {
      Student s = studentDAO.findWithDetails(formStudentId);
      if (s != null) {
        formStudent = s;
        if (s.getUniversity() != null) {
          selectedUniversityId = s.getUniversity().getId();
          // Pre-select only courses that belong to the current university
          List<Long> universityCoursIds = courseDAO.findByUniversity(selectedUniversityId)
              .stream().map(Course::getId).collect(Collectors.toList());
          selectedCourseIds = s.getCourses().stream()
              .map(Course::getId)
              .filter(universityCoursIds::contains)
              .collect(Collectors.toList());
        }
      }
    }
  }

  // --- Add ---

  @Transactional
  public String addStudent() {
    try {

      if (studentDAO.existsByName(formStudent.getFirstName(), formStudent.getLastName())) {
        errorMessage = "A student with this name already exists.";
        return null;
      }
      if (selectedUniversityId != null) {
        formStudent.setUniversity(universityService.getUniversity(selectedUniversityId));
      }

      studentDAO.persist(formStudent);
      enrollCourses(formStudent);

      return "university.xhtml?faces-redirect=true";
    } catch (OptimisticLockException e) {
      errorMessage = "Data was modified by another user. Please refresh and try again.";
      return null;
    }
  }

  // --- Edit ---

  @Transactional
  public String saveStudent() {
    try {
      formStudent.setUniversity(selectedUniversityId != null
          ? universityService.getUniversity(selectedUniversityId) : null);

      // Replace enrolled courses with the current selection
      formStudent.getCourses().clear();
      enrollCourses(formStudent);

      studentDAO.merge(formStudent);
      return "university.xhtml?faces-redirect=true";
    } catch (OptimisticLockException e) {
      errorMessage = "This record was modified by someone else. Choose an option below.";
      hasConflict = true;
      return null;
    }
  }

  /** Overwrites the DB record with the current form data, ignoring the conflict. */
  @Transactional
  public String overwriteStudent() {
    Student latest = studentDAO.find(formStudent.getId());
    latest.setFirstName(formStudent.getFirstName());
    latest.setLastName(formStudent.getLastName());
    latest.setEmail(formStudent.getEmail());
    latest.setUniversity(selectedUniversityId != null
        ? universityService.getUniversity(selectedUniversityId) : null);
    latest.getCourses().clear();
    enrollCourses(latest);
    studentDAO.merge(latest);
    return "university.xhtml?faces-redirect=true";
  }

  /** Reloads fresh data from DB so the user can re-edit without conflict. */
  public String refreshAndRetry() {
    return "editStudent.xhtml?faces-redirect=true&id=" + formStudent.getId();
  }

  private void enrollCourses(Student student) {
    for (Long courseId : selectedCourseIds) {
      Course course = courseDAO.find(courseId);
      if (course != null) student.getCourses().add(course);
    }
  }

  // --- Delete ---

  @Transactional
  public String deleteStudent(Long id) {
    studentDAO.delete(id);
    return "university.xhtml?faces-redirect=true";
  }
}
