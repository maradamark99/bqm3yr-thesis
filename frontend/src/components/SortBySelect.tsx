import { OrderBy, PageableOptions } from '../types';

export function SortBySelect({
	values,
	label,
	pageAbleOptions,
	setPageAbleOptions,
}: {
	values: OrderBy[];
	label: string;
	pageAbleOptions: PageableOptions;
	setPageAbleOptions: React.Dispatch<React.SetStateAction<PageableOptions>>;
}) {
	return (
		<div className="self-end mt-4 mr-20">
			<label className="mr-2">{label}</label>
			<select
				className="p-2 rounded"
				value={pageAbleOptions.sortOption.orderBy}
				onChange={(e) => {
					const orderByValue = values
						.filter((v) => v.label === e.target.value)
						.pop();
					setPageAbleOptions({
						...pageAbleOptions,
						sortOption: {
							direction: orderByValue!.direction,
							orderBy: orderByValue!.value,
						},
					});
				}}
			>
				{values.map((value, i) => (
					<option key={i} value={value.label}>
						{value.label}
					</option>
				))}
			</select>
		</div>
	);
}
