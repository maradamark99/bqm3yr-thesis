import { Pagination } from '@mui/material';
import { Pageable } from '../types';

export function Paginator<T>({
	className,
	pageable,
	onChange,
}: {
	className: string;
	pageable: Pageable<T> | undefined;
	onChange: (page: number) => void;
}) {
	return (
		<Pagination
			siblingCount={1}
			className={className}
			count={pageable ? pageable.totalPages : 1}
			page={pageable ? pageable.number + 1 : 1}
			onChange={(event, page) => {
				onChange(page);
			}}
		/>
	);
}
