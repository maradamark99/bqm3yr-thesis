import { useEffect, useState } from 'react';
import { Product } from '../types/product';
import { getProducts } from '../services/product';
import { ProductCard } from './ProductCard';

export function ProductCatalog() {
	const [products, setProducts] = useState<Product[]>([]);

	useEffect(() => {
		getProducts()
			.then((res) => {
				if (res.ok) {
					return res.json();
				} else {
					throw new Error('Failed to fetch products');
				}
			})
			.then((data) => {
				setProducts(data);
			})
			.catch((err) => {
				console.log(err);
			});
	}, []);

	return (
		<div className="h-2/3 pt-10 grid sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
			{products.map((product) => (
				<div
					key={product.id}
					className="flex justify-center items-center"
				>
					<ProductCard product={product} />
				</div>
			))}
		</div>
	);
}
