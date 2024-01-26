import { useEffect, useState } from "react";
import { API_URL } from "../config";
import { Pageable, Product } from "../types";

export function useProducts(): Pageable<Product>{
    const [products, setProducts] = useState<Pageable<Product> | undefined>();

	useEffect(() => {
		fetch(`${API_URL}/api/v1/products`)
			.then((res) => {
				if (res.ok) {
					return res.json();
				} else {
					throw new Error('Failed to fetch products');
				}
			})
            .then((data: Pageable<Product>) => {
				setProducts(data);
			})
			.catch((err) => {
				console.log(err);
			});
    }, []);
    
    return products!;
}