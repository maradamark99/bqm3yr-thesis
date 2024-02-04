import { useEffect, useState } from "react";
import { Pageable, PageableOptions, Product, ProductView } from "../types";

const API_URL = import.meta.env.VITE_API_URL;

export function useProducts() {
	const [category, setCategory] = useState<string>();
    const [products, setProducts] = useState<Pageable<Product> | undefined>();
	const [pageableOptions, setPageableOptions] = useState<PageableOptions>({
		page: 1,
		sortOption: {
			orderBy: 'default',
			direction: 'asc',
		},
	});
	useEffect(() => {
		getProducts(pageableOptions, category).then(setProducts);
    }, [pageableOptions, category]);
	return {
		category,
		setCategory,
		products,
		setProducts,
		pageableOptions,
		setPageableOptions
	};
}

export function useProductById(id: string) {
	const [product, setProduct] = useState<ProductView>();
	useEffect(() => {
		fetch(`${API_URL}/api/v1/products/${id}`)
			.then(res => res.json())
			.then(setProduct);
	}, []);
	return product;

}

async function getProducts(pageableOptions: PageableOptions, category?: string, ) {
	const params = new URLSearchParams();
	if (category) {
		params.set('category', category);
	}
	params.set('page', String(pageableOptions.page - 1));
	if (pageableOptions.sortOption.orderBy !== 'default') {
		params.set('sort', `${pageableOptions.sortOption.orderBy},${pageableOptions.sortOption.direction}`);
	}
	const res = await fetch(`${API_URL}/api/v1/products?${params.toString()}`)
	if (!res.ok) {
		throw new Error('Failed to fetch products');
	}
	const data = await res.json();
	return data
}