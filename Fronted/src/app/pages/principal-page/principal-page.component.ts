import { ChangeDetectorRef, Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { DonutChartComponent } from '@app/components/chart-graphics/chart-doughnut.component';
import { AnalysisIconComponent } from '@app/components/Icons/analysis-icon/analysis-icon.component';
import { DeleteIconComponent } from '@app/components/Icons/delete-icon/delete-icon.component';
import { SeeIconComponent } from '@app/components/Icons/edit-icon/see-icon.component';
import { MoreIconComponent } from '@app/components/Icons/more-icon/more-icon.component';
import { InspectiaAddEntityFormComponent } from '@app/components/inspectia-add-entity-form/inspectia-add-entity-form.component';
import { BasicModalComponent } from '@app/components/Modals/basic-modal/basic-modal.component';
import { Analysis } from '@app/core/models/Analysis/analysis.model';
import { AnalysisService } from '@app/core/services/analysis.service';
import { AuthService } from '@app/core/services/auth.service';
import { IconAddComponent } from '@components/Icons/icon-add/icon-add.component';
import { LogoutIconComponent } from '@components/Icons/logout-icon/logout-icon.component';
import { InspectiaButtonComponent } from '@components/inspectia-button/inspectia-button.component';
import { InspectiaIconTextComponent } from '@components/inspectia-icon-text/inspectia-icon-text.component';
import { InspectiaLogoContainerComponent } from '@components/inspectia-logo-container/inspectia-logo-container.component';
import { RowTableComponent } from '@components/table-info/row-table/row-table.component';
import { ValueRowComponent } from '@components/table-info/value-row/value-row.component';
import { statesDefect } from 'assets/Defaults/Default-values';
import { modalTypes, state, typeOperationsForm } from 'assets/Typescript-generalities/Types';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-principal-page',
  standalone: true,
  imports: [
    InspectiaLogoContainerComponent,
    InspectiaIconTextComponent,
    BasicModalComponent,
    LogoutIconComponent,
    InspectiaButtonComponent,
    RowTableComponent,
    ValueRowComponent,
    IconAddComponent,
    SeeIconComponent,
    DeleteIconComponent,
    DonutChartComponent,
    InspectiaAddEntityFormComponent,
    MoreIconComponent,
    ProgressSpinnerModule,
    AnalysisIconComponent

  ],
  templateUrl: 'principal-page.component.html',
  styleUrl: 'principal-page.component.css'
})
export class PrincipalPageComponent implements OnInit {

  id: string = "";
  operation = "";
  data: Analysis[] = [];
  isLoading: boolean = false;
  sectionDescription = "";
  showModal: boolean = false;
  typeModal: modalTypes = 'info';
  color: boolean = true;
  currentPercentageValue: number = 0.0
  operationActual: typeOperationsForm = 'none';
  optionsState: state[] = statesDefect;
  showForm = false;
  public expandedRows = new Set<string>();
  @ViewChild('fileUploadInput') fileUploadInput!: InspectiaAddEntityFormComponent;
  @ViewChild('modal') modal!: BasicModalComponent;
  errorCounter: number = 0
  customModalMessage: modalTypes = ""

  closedModalExecute() {
    this.modal.defaultMessages = true;
    this.showModal = false;
  }

  toggleRow(uuid: string): void {
    if (this.expandedRows.has(uuid)) {
      this.expandedRows.delete(uuid);
    } else {
      this.expandedRows.add(uuid);
    }
  }

  isRowExpanded(uuid: string): boolean {
    return this.expandedRows.has(uuid);
  }

  constructor(
    private renderer: Renderer2,
    private el: ElementRef,
    private router: Router,
    private authService: AuthService,
    private analysisService: AnalysisService,
    private cdref: ChangeDetectorRef
  ) { }

  updatePercentage() {
    if (this.data.length > 0) {
      this.currentPercentageValue = this.data[0].score * 10;
    } else {
      this.currentPercentageValue = 0;
    }
  }

  ngOnInit() {
    this.updateData();
  }
  seeCurrentMetric(score: number): void {
    this.currentPercentageValue = score * 10
  }



  goToLoginPage() {
    this.router.navigate(['/login']);
  };

  closeUpdate() {
    this.showForm = false;
    this.operationActual = "none";
  }


  deleteAnalysis(uuid: string) {
    this.typeModal = 'delete';
    this.showModal = true;
    this.operationActual = "deleteAnalysis";
    this.id = uuid
  }

  generateAnalysis(event: Event) {

    event.preventDefault();
    const element = event.currentTarget as HTMLFormElement;
    let inputFilesElement = element.elements.namedItem("file-selector") as HTMLInputElement;
    let fileList: FileList | null = inputFilesElement.files;
    this.showSpinner()
    if (fileList && fileList.length > 0) {
      this.analysisService.createNewAnalysis(Array.from(fileList)).pipe(
        finalize(() => {
          this.showGenericSuccessModal();
          this.hideSpinner()
        })
      ).subscribe({
        error: (error) => {
          this.fileUploadInput.clearFilesInput();
          console.error('Error al crear el análisis:', error);
          this.operationActual = "generateAnalysis";
          this.showForm = false;
          this.showGenericErrorModal();
        },
        complete: () => {
          this.fileUploadInput.clearFilesInput();
          this.operationActual = "generateAnalysis";
          this.showForm = false;
          this.updateData();

        },
      });
    } else {
      this.operationActual = "none";
      this.typeModal = "info";
      this.customModalMessage = "emptyFields"
      this.modal.defaultMessages = false;
      this.showModal = true;
      this.hideSpinner();
    }
  }

  showGenericErrorModal() {
    this.typeModal = "error";
    this.showModal = true;
  }

  showGenericSuccessModal() {
    this.typeModal = "success";
    this.showModal = true;
  }

  showSpinner() {
    this.isLoading = true;
  }

  hideSpinner() {
    this.isLoading = false;
  }

  updateData() {

    this.showSpinner()
    this.analysisService.getAnalysisByUser().pipe(
      finalize(() => {
        this.hideSpinner()
      })
    ).subscribe(
      {
        next: (data) => {
          this.data = data;
          this.updatePercentage();
        },
        error: (err) => {
          console.log(err);
        }
      }
    );
  }

  getDate(value: Analysis) {
    let dateString = new Date(value.validationDate);

    let date = new Date(dateString.toUTCString());
    return date.toLocaleDateString("es", { weekday: "long", year: "numeric", month: "short", day: "numeric", hour: "2-digit", minute: "2-digit", second: "2-digit" })
  }

  showFormAnalysisGeneration() {
    this.operationActual = 'generateAnalysis';
    this.typeModal = "warning"
    this.showForm = true;
  }

  logout() {
    this.operationActual = 'logout';
    this.typeModal = 'warning';
    this.showModal = true;
  }

  executeActualOperation(element: any) {

    if (this.operationActual == 'logout') {
      this.authService.logout().subscribe({
        next: () => {
          this.goToLoginPage();
        },
        error: () => {
          this.goToLoginPage();
        }
      })
    } else if (element && this.operationActual === "deleteAnalysis") {
      this.showSpinner()
      this.showModal = false;
      this.analysisService.deleteAnalysisByHash(element as string).pipe(
        finalize(() => {
          this.hideSpinner()
          this.showGenericSuccessModal();
        })
      ).subscribe(
        {
          error: () => {
            this.showGenericErrorModal();
            if (this.errorCounter > 3) {
              this.logout()
            }

            this.errorCounter++;
          },
          complete: () => {
            this.updateData();
            this.showModal = false;
          }
        }
      );
    }
  }

}
