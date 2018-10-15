package jkmkowalczyk.coreservicestask.request;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * Main entity object for Request.
 */
@Entity
@Table
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String clientId;
    @Column
    private Long requestId;
    @Column
    private String name;
    @Column
    private Integer quantity;
    @Column
    private Double price;

    /**
     * Main constructor for Request.
     *
     * @param builder builder to create Request object
     */
    private Request(final Builder builder) {
        this.id = builder.id;
        this.clientId = builder.clientId;
        this.requestId = builder.requestId;
        this.name = builder.name;
        this.quantity = builder.quantity;
        this.price = builder.price;
    }

    public String getClientId() {
        return clientId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
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
        Request request = (Request) o;
        return Objects.equals(id, request.id)
                && Objects.equals(clientId, request.clientId)
                && Objects.equals(requestId, request.requestId)
                && Objects.equals(name, request.name)
                && Objects.equals(quantity, request.quantity)
                && Objects.equals(price, request.price);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, clientId, requestId, name, quantity, price);
    }

    @Override
    public final String toString() {
        return "Request{"
                + "id=" + id
                + ", clientId=" + clientId
                + ", requestId=" + requestId
                + ", name='" + name + '\''
                + ", quantity=" + quantity
                + ", price=" + price
                + '}';
    }

    /**
     * Builder class for Request.
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
         * Creates Request object.
         *
         * @return built Request object
         */
        public Request build() {
            return new Request(this);
        }
    }

    /**
     * Creates new RequestDto object based on Request's properties.
     *
     * @return built RequestDto object
     */
    final RequestDto toDto() {
        return new RequestDto.Builder()
                .id(id)
                .clientId(clientId)
                .requestId(requestId)
                .name(name)
                .quantity(quantity)
                .price(price)
                .build();
    }
}
