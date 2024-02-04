import { createContext, useContext } from 'react';
import { OrderBy, Pageable, PageableOptions, Product } from '../types';
import { useProducts } from '../hooks/product';
import { CategoryList } from '../components/CategoryList';
import { SortBySelect } from '../components/SortBySelect';
import { ProductCatalog } from '../components/ProductCatalog';
import { Paginator } from '../components/Paginator';

const orderByValues: OrderBy[] = [
	{
		value: 'default',
		label: 'Default',
	},
	{
		value: 'currentPrice',
		label: 'Price - low to high',
		direction: 'asc',
	},
	{
		value: 'currentPrice',
		label: 'Price - high to low',
		direction: 'desc',
	},
	{
		value: 'name',
		label: 'Name - (A-Z)',
		direction: 'asc',
	},
	{
		value: 'name',
		label: 'Name - (Z-A)',
		direction: 'desc',
	},
	{
		value: 'avgRating',
		label: 'Rating',
		direction: 'desc',
	},
];
const ProductContext = createContext<
	| {
			products: Pageable<Product> | undefined;
			setProducts: React.Dispatch<
				React.SetStateAction<Pageable<Product> | undefined>
			>;
			pageableOptions: PageableOptions;
			setPageableOptions: React.Dispatch<
				React.SetStateAction<PageableOptions>
			>;
			category: string | undefined;
			setCategory: React.Dispatch<
				React.SetStateAction<string | undefined>
			>;
	  }
	| undefined
>(undefined);

export function useProductContext() {
	const context = useContext(ProductContext);
	if (context === undefined) {
		throw new Error(
			'useProductContext must be used within a ProductContext'
		);
	}
	return context;
}

export function Home() {
	return (
		<div className="flex h-screen bg-slate-50">
			<ProductContext.Provider value={useProducts()}>
				<CategoryList className="w-1/6 h-screen p-10 bg-white shadow-lg flex flex-col items-center" />
				<div className="w-5/6 h-screen flex flex-col items-center gap-3">
					<ProductContext.Consumer>
						{(context) => (
							<>
								<SortBySelect
									values={orderByValues}
									pageAbleOptions={context!.pageableOptions}
									setPageAbleOptions={
										context!.setPageableOptions
									}
									label="Sort by:"
								/>

								<ProductCatalog
									products={context!.products}
									className="w-full px-24 grid sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 overflow-scroll"
								/>
								<Paginator
									className="my-3"
									pageable={context!.products}
									onChange={(e) =>
										context!.setPageableOptions({
											...context!.pageableOptions,
											page: e,
										})
									}
								/>
							</>
						)}
					</ProductContext.Consumer>
				</div>
			</ProductContext.Provider>
		</div>
	);
}
