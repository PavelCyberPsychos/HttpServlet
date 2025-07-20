package validator;

public interface Validator<T> {

    ValidationResult IsValid(T object);
}
