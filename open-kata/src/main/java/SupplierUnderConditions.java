import java.util.function.Supplier;

public class SupplierUnderConditions<T>  {
    Supplier<T> supplier;

    public SupplierUnderConditions(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <U> SupplierUnderConditions<U> from(Supplier<U> supplier) {
        return new SupplierUnderConditions<>(supplier);
    }

    public SupplierUnderConditions<T> or(Supplier<T> supplier) {
        return new SupplierUnderConditions<>(
                () -> {
                    T currentSupplier = this.supplier.get();
                    if (currentSupplier == null) return supplier.get();
                    else return currentSupplier;
                });
    }

    public T orDefault(Supplier<T> supplier) {
        T currentSupplier = this.supplier.get();
        if (currentSupplier == null) return supplier.get();
        else return currentSupplier;
    }

}
