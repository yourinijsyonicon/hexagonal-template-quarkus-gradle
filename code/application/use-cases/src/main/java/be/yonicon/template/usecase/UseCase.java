package be.yonicon.template.usecase;

public interface UseCase<USE_CASE_COMMAND, USE_CASE_PRESENTER> {
    void execute(USE_CASE_COMMAND command, USE_CASE_PRESENTER presenter);
}
