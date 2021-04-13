package com.epam.cloud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "categories")
public class Category implements Serializable
{
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>();

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

    public Set<Product> getProducts()
    {
        return products;
    }

    public void setProducts(final Set<Product> products)
    {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category that = (Category) o;
        return id.equals(that.id) &&
                code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}
