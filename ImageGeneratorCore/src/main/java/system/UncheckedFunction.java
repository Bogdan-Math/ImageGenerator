package system;

import java.util.function.Function;

@FunctionalInterface
public interface UncheckedFunction<T, R> extends Function<T, R> {

    R uncheckedApply(T t) throws Throwable;

    @Override
    default R apply(T t) {
        try {
            return uncheckedApply(t);
        } catch (Throwable throwable) {
            throw new UncheckedFunctionException(throwable);
        }
    }

    class UncheckedFunctionException extends RuntimeException {
        UncheckedFunctionException(Throwable throwable) {
            super(throwable);
        }
    }

}