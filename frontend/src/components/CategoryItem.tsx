import { Category } from '../types';
import { useSubCategories } from '../hooks/category';
import { useProductContext } from '../pages/Home';

export function CategoryItem({
	category,
	className,
	setCategories,
}: {
	category: Category;
	className?: string;
	setCategories: React.Dispatch<React.SetStateAction<Category[][]>>;
}) {
	const { setPageableOptions, setCategory } = useProductContext();

	async function onClick() {
		if (category.leaf) {
			const pageableOptions = {
				page: 1,
				sortOption: {
					orderBy: 'default',
				},
			};
			setPageableOptions(pageableOptions);
			setCategory(category.value);
		} else {
			useSubCategories(category.id).then((data) => {
				setCategories((prev) => [...prev, data]);
			});
		}
	}
	return (
		<div className={className}>
			<div
				onClick={onClick}
				className="flex justify-center cursor-pointer items-center"
			>
				<span>{category.value}</span>
				{!category.leaf && (
					<svg
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						stroke="currentColor"
						strokeWidth={1.5}
						className="w-5 h-5 self-end my-auto"
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="m8.25 4.5 7.5 7.5-7.5 7.5"
						/>
					</svg>
				)}
			</div>
		</div>
	);
}
