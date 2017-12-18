package system;

import java.util.function.BiFunction;

@FunctionalInterface
public interface UncheckedBiFunction<T, U, R> extends BiFunction<T, U, R> {

    R uncheckedApply(T t, U u) throws Throwable;

    @Override
    default R apply(T t, U u) {
        try {
            return uncheckedApply(t, u);
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