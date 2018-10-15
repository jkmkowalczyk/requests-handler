package jkmkowalczyk.coreservicestask.request;

import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Objects;

import static jkmkowalczyk.coreservicestask.utils.NumberFormatUtil.setUpNumberFormat;


/**
 * Data transfer object for Request.
 */
@NoArgsConstructor
public class RequestDto {
    private Long id;
    private String clientId;
    private Long requestId;
    private String name;
    private Integer quantity;
    private Double price;

    /**
     * Main constructor for RequestDto.
     *
     * @param builder builder to create RequestDto object
     */
    private RequestDto(final Builder builder) {
        this.id = builder.id;
        this.clientId = builder.clientId;
        this.requestId = builder.requestId;
        this.name = builder.name;
        this.quantity = builder.quantity;
        this.price = builder.price;
    }

    public final String getClientId() {
        return clientId;
    }

    public final Long getRequestId() {
        return requestId;
    }

    public final String getName() {
        return name;
    }

    public final Integer getQuantity() {
        return quantity;
    }

    public final Double getPrice() {
        return price;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestDto that = (RequestDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(clientId, that.clientId)
                && Objects.equals(requestId, that.requestId)
                && Objects.equals(name, that.name)
                && Objects.equals(quantity, that.quantity)
                && Objects.equals(price, that.price);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, clientId, requestId, name, quantity, price);
    }

    @Override
    public final String toString() {
        NumberFormat numberFormat = setUpNumberFormat();
        return "id=" + id
                + ", clientId=" + clientId
                + ", requestId=" + requestId
                + ", name=" + name
                + ", quantity=" + quantity
                + ", price=" + numberFormat.format(price);
    }

    /**
     * Builder class for RequestDto.
     */
    public static final class Builder {
        private Long id;
        private String clientId;
        private Long requestId;
        private String name;
        private Integer quantity;
        private Double price;

        public Builder() {
        }

        public Builder id(final Long val) {
            id = val;
            return this;
        }

        public Builder clientId(final String val) {
            clientId = val;
            return this;
        }

        public Builder requestId(final Long val) {
            requestId = val;
            return this;
        }

        public Builder name(final String val) {
            name = val;
            return this;
        }

        public Builder price(final Double val) {
            price = val;
            return this;
        }

        public Builder quantity(final Integer val) {
            quantity = val;
            return this;
        }

        /**
         * Creates RequestDto object.
         *
         * @return built RequestDto object
         */
        public RequestDto build() {
            return new RequestDto(this);
        }
    }

    /**
     * Creates new Request object based on RequestDto's properties.
     *
     * @return built Request object
     */
    final Request toEntity() {
        return new Request.Builder()
                .id(id)
                .clientId(clientId)
                .requestId(requestId)
                .name(name)
                .quantity(quantity)
                .price(price)
                .build();
    }
}
