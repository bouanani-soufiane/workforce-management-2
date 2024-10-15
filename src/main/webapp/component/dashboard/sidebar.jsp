<!-- Sidebar -->
<aside id="sidebar">
    <div class="sidebar-title">
        <div class="sidebar-brand">
            <span class="material-icons-outlined">inventory</span> Workforce
        </div>
        <span class="material-icons-outlined" onclick="closeSidebar()">close</span>
    </div>

    <ul class="sidebar-list">
        <li class="sidebar-list-item">
            <a href="${pageContext.request.contextPath}/" >
                <span class="material-icons-outlined">dashboard</span> Employees
            </a>
        </li>
        <li class="sidebar-list-item">
            <a href="${pageContext.request.contextPath}/offer">
                <span class="material-icons-outlined">inventory_2</span> offers
            </a>
        </li>
        <li class="sidebar-list-item">
            <a href="${pageContext.request.contextPath}/vacation">
                <span class="material-icons-outlined">inventory_2</span> vacations
            </a>
        </li>

    </ul>
</aside>
<!-- End Sidebar -->