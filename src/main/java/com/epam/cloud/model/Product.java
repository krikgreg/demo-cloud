package com.epam.cloud.model;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "products")
public class Product implements Serializable
{
    @Id
    @JsonIgnore
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "products2categories",
          joinColumns = { @JoinColumn(name = "product_code", referencedColumnName = "code",updatable = false, nullable = false) },
          inverseJoinColumns = { @JoinColumn(name = "category_code", referencedColumnName = "code", updatable = false, nullable = false)})
    private Set<Category> categories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(final BigDecimal price)
    {
        this.price = price;
    }

    public Set<Category> getCategories()
    {
        return categories;
    }

    public void setCategories(final Set<Category> categories)
    {
        this.categories = categories;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final Product product = (Product) o;
        return code.equals(product.code) && name.equals(product.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(code, name);
    }
}
