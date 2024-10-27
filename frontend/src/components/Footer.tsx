export default function Footer() {
	const currentYear = new Date().getFullYear();
	return (
		<>
			<footer className="font-sans">
				<p>A free messaging platform | © {currentYear}</p>
			</footer>
		</>
	);
}
