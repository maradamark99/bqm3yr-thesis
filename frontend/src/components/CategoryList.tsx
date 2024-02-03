import { CategoryItem } from './CategoryItem';
import { useCategories } from '../hooks/category';

export function CategoryList({ className }: { className?: string }) {
	const { categories, setCategories } = useCategories();
	function onBackClick() {
		if (categories.length < 2) return;
		setCategories((prev) => {
			return prev.slice(0, -1);
		});
	}
	return (
		<div className={className}>
			{categories.length > 1 && (
				<svg
					onClick={onBackClick}
					xmlns="http://www.w3.org/2000/svg"
					fill="none"
					viewBox="0 0 24 24"
					strokeWidth={1.5}
					stroke="currentColor"
					className="w-6 h-6 cursor-pointer self-start"
				>
					<path
						strokeLinecap="round"
						strokeLinejoin="round"
						d="M6.75 15.75 3 12m0 0 3.75-3.75M3 12h18"
					/>
				</svg>
			)}
			{categories[categories.length - 1].map((category) => (
				<CategoryItem
					key={category.id}
					category={category}
					className="text-2xl my-2"
					setCategories={setCategories}
				/>
			))}
		</div>
	);
}
